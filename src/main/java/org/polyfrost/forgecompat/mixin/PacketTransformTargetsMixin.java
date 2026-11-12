package org.polyfrost.forgecompat.mixin;

import net.minecraft.network.packet.s2c.play.AddEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.AddExperienceOrbS2CPacket;
import net.minecraft.network.packet.s2c.play.AddGlobalEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.AddMobS2CPacket;
import net.minecraft.network.packet.s2c.play.AddPaintingS2CPacket;
import net.minecraft.network.packet.s2c.play.AddPlayerS2CPacket;
import net.minecraft.network.packet.s2c.play.AttachEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockEventS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockMiningProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlocksUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.CameraS2CPacket;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.CloseInventoryMenuS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.CompressionThresholdS2CPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityDataS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEventS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityHeadAnglesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityMoveS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPickupS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityRemoveStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySyncS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTeleportS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameEventS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryMenuConfirmS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryMenuContentS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryMenuDataS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryMenuSlotContentS2CPacket;
import net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.LoginS2CPacket;
import net.minecraft.network.packet.s2c.play.MapDataS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenInventoryMenuS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenSignEditorS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerCombatS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerHealthS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerInfoS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerMoveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSleepS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerXpS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.ResourcePackS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardScoreS2CPacket;
import net.minecraft.network.packet.s2c.play.SelectSlotS2CPacket;
import net.minecraft.network.packet.s2c.play.SignUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.SoundEventS2CPacket;
import net.minecraft.network.packet.s2c.play.SpawnPointS2CPacket;
import net.minecraft.network.packet.s2c.play.StatsS2CPacket;
import net.minecraft.network.packet.s2c.play.TabListS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.network.packet.s2c.play.TitlesS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldChunkS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldChunksS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({
        KeepAliveS2CPacket.class,
        LoginS2CPacket.class,
        ChatMessageS2CPacket.class,
        WorldTimeS2CPacket.class,
        EntityEquipmentS2CPacket.class,
        SpawnPointS2CPacket.class,
        PlayerHealthS2CPacket.class,
        PlayerRespawnS2CPacket.class,
        PlayerMoveS2CPacket.class,
        SelectSlotS2CPacket.class,
        PlayerSleepS2CPacket.class,
        EntityAnimationS2CPacket.class,
        AddPlayerS2CPacket.class,
        EntityPickupS2CPacket.class,
        AddEntityS2CPacket.class,
        AddMobS2CPacket.class,
        AddPaintingS2CPacket.class,
        AddExperienceOrbS2CPacket.class,
        EntityVelocityS2CPacket.class,
        RemoveEntitiesS2CPacket.class,
        EntityMoveS2CPacket.class,
        EntityTeleportS2CPacket.class,
        EntityHeadAnglesS2CPacket.class,
        EntityEventS2CPacket.class,
        AttachEntityS2CPacket.class,
        EntityDataS2CPacket.class,
        EntityStatusEffectS2CPacket.class,
        EntityRemoveStatusEffectS2CPacket.class,
        PlayerXpS2CPacket.class,
        EntityAttributesS2CPacket.class,
        WorldChunkS2CPacket.class,
        BlocksUpdateS2CPacket.class,
        BlockUpdateS2CPacket.class,
        BlockEventS2CPacket.class,
        BlockMiningProgressS2CPacket.class,
        WorldChunksS2CPacket.class,
        ExplosionS2CPacket.class,
        WorldEventS2CPacket.class,
        SoundEventS2CPacket.class,
        ParticleS2CPacket.class,
        GameEventS2CPacket.class,
        AddGlobalEntityS2CPacket.class,
        OpenInventoryMenuS2CPacket.class,
        CloseInventoryMenuS2CPacket.class,
        InventoryMenuSlotContentS2CPacket.class,
        InventoryMenuContentS2CPacket.class,
        InventoryMenuDataS2CPacket.class,
        InventoryMenuConfirmS2CPacket.class,
        SignUpdateS2CPacket.class,
        MapDataS2CPacket.class,
        BlockEntityUpdateS2CPacket.class,
        OpenSignEditorS2CPacket.class,
        StatsS2CPacket.class,
        PlayerInfoS2CPacket.class,
        PlayerAbilitiesS2CPacket.class,
        CommandSuggestionsS2CPacket.class,
        ScoreboardObjectiveS2CPacket.class,
        ScoreboardScoreS2CPacket.class,
        ScoreboardDisplayS2CPacket.class,
        TeamS2CPacket.class,
        CustomPayloadS2CPacket.class,
        DisconnectS2CPacket.class,
        DifficultyS2CPacket.class,
        PlayerCombatS2CPacket.class,
        CameraS2CPacket.class,
        WorldBorderS2CPacket.class,
        TitlesS2CPacket.class,
        CompressionThresholdS2CPacket.class,
        TabListS2CPacket.class,
        ResourcePackS2CPacket.class,
        EntitySyncS2CPacket.class
})
public abstract class PacketTransformTargetsMixin {
}
