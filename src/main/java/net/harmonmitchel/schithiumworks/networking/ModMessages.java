package net.harmonmitchel.schithiumworks.networking;

import net.harmonmitchel.schithiumworks.SCHITHIUMWORKS;
import net.harmonmitchel.schithiumworks.networking.packet.EnergySyncPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
     private static SimpleChannel INSTANCE;
     private static int packetID=0;
     private static int id(){
         return packetID++;
     }
     public static void register(){
         SimpleChannel net = NetworkRegistry.ChannelBuilder
                 .named(new ResourceLocation(SCHITHIUMWORKS.MOD_ID, "messages"))
                 .networkProtocolVersion(()-> "1.0")
                 .clientAcceptedVersions(s -> true)
                 .serverAcceptedVersions(s -> true)
                 .simpleChannel();
         INSTANCE = net;


         net.messageBuilder(EnergySyncPacket.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                 .decoder(EnergySyncPacket::new)
                 .encoder(EnergySyncPacket::toBytes)
                 .consumerMainThread(EnergySyncPacket::handle)
                 .add();

     }
     public static <MSG> void sendToServer(MSG message){
         INSTANCE.sendToServer(message);
     }
     public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
         INSTANCE.send(PacketDistributor.PLAYER.with(()->player),message);
     }
     public static <MSG> void  sendToClients(MSG message){
         INSTANCE.send(PacketDistributor.ALL.noArg(), message);
     }



}
