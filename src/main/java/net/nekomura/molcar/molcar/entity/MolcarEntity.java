package net.nekomura.molcar.molcar.entity;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.nekomura.molcar.molcar.Molcar;
import net.nekomura.molcar.molcar.registry.ModEntities;
import net.nekomura.molcar.molcar.registry.ModItems;
import net.nekomura.molcar.molcar.registry.ModSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class MolcarEntity extends TameableEntity  implements Inventory, NamedScreenHandlerFactory {

    private int eatingTime;
    private static final TrackedData<Integer> MOLCAR_TYPE;
    private static final Predicate<ItemEntity> PICKABLE_DROP_FILTER;
    public static final Map<Integer, Identifier> TEXTURES;
    private DefaultedList<ItemStack> inventory;

    static {
        MOLCAR_TYPE = DataTracker.registerData(MolcarEntity.class, TrackedDataHandlerRegistry.INTEGER);
        PICKABLE_DROP_FILTER = (itemEntity) -> !itemEntity.cannotPickup()
                                && itemEntity.isAlive()
                                && itemEntity.getStack().isFood()
                                && (itemEntity.getStack().getItem().equals(ModItems.LETTUCE_LEAF)
                                    || itemEntity.getStack().getItem().equals(ModItems.LIGHTNING_CARROT)
                                    || itemEntity.getStack().getItem().equals(Items.CARROT)
                                    || itemEntity.getStack().getItem().equals(Items.BREAD)
        );
        TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
            hashMap.put(0, new Identifier(Molcar.MOD_ID, "textures/entity/molcar/potato.png"));
        });
    }

    public MolcarEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
        this.setTamed(false);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }

    public static DefaultAttributeContainer getAttributeContainer() {
        return MobEntity
                .createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
                .build();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MolcarEntity.PickupItemGoal());
        this.goalSelector.add(3, new MolcarEntity.WanderAroundFarGoal(this, 1.0F));
    }

    protected void initAttributes() {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(100.0F);
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3F);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MOLCAR_TYPE, 0);
    }

    public int getMolcarType() {
        return this.dataTracker.get(MOLCAR_TYPE);
    }

    public void setMolcarType(int type) {
        if (type != 0) {
            type = this.random.nextInt(1);
        }

        this.dataTracker.set(MOLCAR_TYPE, type);
    }

    public Identifier getTexture() {
        return TEXTURES.getOrDefault(this.getMolcarType(), TEXTURES.get(0));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 100f);

        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(0.2F);
        }

        this.initAttributes();
        this.setHealth(this.getMaxHealth());
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    public void tickMovement() {
        if (!this.world.isClient && this.isAlive() && this.canMoveVoluntarily()) {
            if (!this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && this.getEquippedStack(EquipmentSlot.MAINHAND).isFood()) {
                ++this.eatingTime;
                ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
                if (this.canEat(itemStack)) {
                    if (this.eatingTime > 250) {
                        ItemStack itemStack2 = itemStack.finishUsing(this.world, this);
                        if (!itemStack2.isEmpty()) {
                            this.equipStack(EquipmentSlot.MAINHAND, itemStack2);
                        }

                        this.eatingTime = 0;
                        this.playSound(ModSoundEvents.MOLCAR_FEED, 1.0f, 1.0f);
                    }
                } else if (this.eatingTime > 200 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemStack), 1.5F, 1.0F);
                    this.world.sendEntityStatus(this, (byte) 45);
                }
            }
        }

        super.tickMovement();
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(250.0F);
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.92F);
            this.setHealth(250.F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(100.0F);
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3F);
        }
    }

    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.MOLCAR_HAPPY;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.MOLCAR_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.MOLCAR_DIE;
    }

    public SoundEvent getEatSound(ItemStack stack) {
        return ModSoundEvents.MOLCAR_EAT;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!state.getMaterial().isLiquid()) {
            BlockState blockState = this.world.getBlockState(pos.up());
            BlockSoundGroup blockSoundGroup = state.getSoundGroup();
            if (blockState.isOf(Blocks.SNOW)) {
                blockSoundGroup = blockState.getSoundGroup();
            }

            this.playSound(ModSoundEvents.MOLCAR_STEP, blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
        }
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isFood() && (this.onGround || this.submergedInWater || this.touchingWater);
    }

    protected void eat(PlayerEntity player, ItemStack stack) {
        if (stack.getItem().isFood()) {
            this.playSound(this.getEatSound(stack), 1.0F, 1.0F);
        }

        super.eat(player, stack);
    }

    public boolean isCollidable() {
        return true;
    }

    public boolean isPushable() {
        return true;
    }

    public double getMountedHeightOffset() {
        return -0.1D;
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.hasPassengers() && source.getAttacker() == this.getPassengerList().get(0)) {
            return false;
        }else {
            return super.damage(source, amount);
        }
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        if (onGround) {
            if (this.fallDistance > 0.0F) {
                landedState.getBlock().onLandedUpon(this.world, landedPosition, this, 0.5F * this.fallDistance);
            }

            this.fallDistance = 0.0F;
        } else if (heightDifference < 0.0D) {
            this.fallDistance = (float)((double)this.fallDistance - heightDifference);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.MOLCAR.create(world);
    }

    private boolean wantsToPickupItem() {
        return true;
    }

    public boolean canPickupItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack equipItem = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return equipItem.isEmpty() || this.eatingTime > 0 && item.isFood() && !equipItem.getItem().isFood();
    }

    protected void loot(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getStack();
        Item item = itemStack.getItem();
        if (item == Items.CARROT || item == Items.BREAD || item == ModItems.LETTUCE_LEAF || item == ModItems.LIGHTNING_CARROT) {
            if (this.canPickupItem(itemStack)) {
                int i = itemStack.getCount();
                if (i > 1) {
                    this.dropItem(itemStack.split(i - 1));
                }

                this.method_29499(itemEntity);
                this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
                this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;
                this.sendPickup(itemEntity, itemStack.getCount());

                itemEntity.remove();
                this.eatingTime = 0;
            }
        }
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);
        this.world.spawnEntity(itemEntity);
    }

    protected void drop(DamageSource source) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.dropItem(itemStack);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.drop(source);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == ModItems.LETTUCE_LEAF || stack.getItem() == ModItems.LIGHTNING_CARROT || stack.getItem() == Items.BREAD || stack.getItem() == Items.CARROT;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (this.world.isClient) {  //client
            if (this.isTamed() && this.isOwner(player)) {
                return ActionResult.SUCCESS;
            }else {
                return !this.isBreedingItem(itemStack) || !(this.getHealth() < this.getMaxHealth()) && this.isTamed() ? ActionResult.PASS : ActionResult.SUCCESS;
            }
        }else {  //server
            if (this.isTamed()) {
                if (this.isOwner(player)) {  //如果互動玩家為主人
                    if (player.isSneaking()) {
                        player.openHandledScreen(this);
                        if (!player.world.isClient) {
                            return ActionResult.CONSUME;
                        } else {
                            return ActionResult.SUCCESS;
                        }
                    }

                    if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                        if (!player.abilities.creativeMode) {
                            itemStack.decrement(1);
                        }

                        this.heal(itemStack.getItem().getFoodComponent().getHunger() * 2F);
                        return ActionResult.SUCCESS;
                    }

                    if (this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()   //如果天竺鼠車車手上並沒有物品
                            && this.isBreedingItem(itemStack)) {  //且玩家手上拿著生菜、胡蘿蔔、麵包任一種
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
                        this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 2.0F;

                        this.eatingTime = 0;

                    } else {
                        player.startRiding(this);
                        player.setHeadYaw(this.yaw);
                    }
                    return ActionResult.success(this.world.isClient);
                }else {
                    return ActionResult.PASS;
                }
            }else {  //如果未馴服
                if (itemStack.getItem() == ModItems.LETTUCE_LEAF) {
                    if (!player.abilities.creativeMode) {
                        itemStack.decrement(1);
                    }

                    this.eat(player, itemStack);

                    if (this.random.nextInt(3) == 0) {
                        this.setOwner(player);
                        this.navigation.stop();
                        this.setTarget(null);
                        this.world.sendEntityStatus(this, (byte) 7);
                    } else {
                        this.world.sendEntityStatus(this, (byte) 6);
                    }

                    return ActionResult.CONSUME;
                }else {
                    if (this.hasPassengers()) {
                        return ActionResult.PASS;
                    }

                    return ActionResult.PASS;
                }
            }
        }
    }

    protected boolean isImmobile() {
        return super.isImmobile() && this.hasPassengers();
    }

    public void updatePassengerPosition(Entity passenger) {
        if (this.hasPassenger(passenger)) {
            passenger.updatePosition(this.getX(), this.getY() - 0.353d, this.getZ());
        }
    }

    public Direction getMovementDirection() {
        return this.getHorizontalFacing().rotateYClockwise();
    }

    public boolean canBeControlledByRider() {
        return true;
    }

    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.hasPassengers() && this.canBeControlledByRider() && this.getPassengerList().get(0) instanceof PlayerEntity) {
                LivingEntity livingEntity = (LivingEntity)this.getPassengerList().get(0);
                this.yaw = livingEntity.yaw;
                this.prevYaw = this.yaw;
                this.setRotation(this.yaw, this.pitch);
                this.bodyYaw = this.yaw;
                this.headYaw = this.bodyYaw;
                this.stepHeight = 1.0F;
                this.flyingSpeed = this.getMovementSpeed() * 0.1F;
                float f = 0;
                float g = livingEntity.forwardSpeed;

                if (g <= 0.0F) {
                    g *= 0.25F;
                }

                if (this.isLogicalSideForUpdatingMovement()) {
                    this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    super.travel(new Vec3d((double)f, movementInput.y, (double)g));
                } else if (livingEntity instanceof PlayerEntity) {
                    this.setVelocity(Vec3d.ZERO);
                }

                this.method_29242(this, false);
            }else {
                super.travel(movementInput);
            }
        }
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putBoolean("Tame", this.isTamed());
        if (this.getOwnerUuid() != null) {
            tag.putUuid("Owner", this.getOwnerUuid());
        }
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setTamed(tag.getBoolean("Tame"));
        UUID uUID;
        if (tag.containsUuid("Owner")) {
            uUID = tag.getUuid("Owner");
        } else {
            String string = tag.getString("Owner");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID != null) {
            this.setOwnerUuid(uUID);
        }
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        Iterator var1 = this.inventory.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack itemStack = (ItemStack)this.inventory.get(slot);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.inventory.set(slot, ItemStack.EMPTY);
            return itemStack;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.removed) {
            return false;
        } else {
            return player.squaredDistanceTo(this) <= 64.0D && this.isOwner(player);
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        if (player.isSpectator()) {
            return null;
        } else {
            return this.getScreenHandler(syncId, inv);
        }
    }

    public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, this, 1);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    public boolean equip(int slot, ItemStack item) {
        if (slot >= 0 && slot < this.size()) {
            this.setStack(slot, item);
            return true;
        } else {
            return false;
        }
    }

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            if (!MolcarEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (MolcarEntity.this.getTarget() == null && MolcarEntity.this.getAttacker() == null) {
                if (!MolcarEntity.this.wantsToPickupItem()) {
                    return false;
                }else {
                    List<ItemEntity> list = MolcarEntity.this.world.getEntitiesByClass(ItemEntity.class, MolcarEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), MolcarEntity.PICKABLE_DROP_FILTER);
                    return !list.isEmpty() && MolcarEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = MolcarEntity.this.world.getEntitiesByClass(ItemEntity.class, MolcarEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), MolcarEntity.PICKABLE_DROP_FILTER);
            ItemStack itemStack = MolcarEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                MolcarEntity.this.getNavigation().startMovingTo((Entity)list.get(0), 1.2000000476837158D);
            }

        }

        public void start() {
            List<ItemEntity> list = MolcarEntity.this.world.getEntitiesByClass(ItemEntity.class, MolcarEntity.this.getBoundingBox().expand(8.0D, 8.0D, 8.0D), MolcarEntity.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) {
                MolcarEntity.this.getNavigation().startMovingTo((Entity)list.get(0), 1.2000000476837158D);
            }

        }
    }

    class WanderAroundFarGoal extends WanderAroundGoal {
        private boolean field_24463 = true;
        protected final float probability;

        public WanderAroundFarGoal(PathAwareEntity pathAwareEntity, double d) {
            this(pathAwareEntity, d, 0.001F);
        }

        public WanderAroundFarGoal(PathAwareEntity mob, double speed, float probability) {
            super(mob, speed);
            this.probability = probability;
        }

        public boolean canStart() {
            if ((this.mob.getDataTracker().get(TAMEABLE_FLAGS) & 4) == 0) {  //相當於 this.mob.isTamed()
                if (this.mob.hasPassengers()) {
                    return false;
                } else {
                    if (!this.ignoringChance) {
                        if (this.field_24463 && this.mob.getDespawnCounter() >= 100) {
                            return false;
                        }

                        if (this.mob.getRandom().nextInt(this.chance) != 0) {
                            return false;
                        }
                    }

                    Vec3d vec3d = this.getWanderTarget();
                    if (vec3d == null) {
                        return false;
                    } else {
                        this.targetX = vec3d.x;
                        this.targetY = vec3d.y;
                        this.targetZ = vec3d.z;
                        this.ignoringChance = false;
                        return true;
                    }
                }
            }else {
                return false;
            }
        }

        @Nullable
        protected Vec3d getWanderTarget() {
            if (this.mob.isInsideWaterOrBubbleColumn()) {
                Vec3d vec3d = TargetFinder.findGroundTarget(this.mob, 15, 7);
                return vec3d == null ? super.getWanderTarget() : vec3d;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ? TargetFinder.findGroundTarget(this.mob, 10, 7) : super.getWanderTarget();
            }
        }
    }
}
