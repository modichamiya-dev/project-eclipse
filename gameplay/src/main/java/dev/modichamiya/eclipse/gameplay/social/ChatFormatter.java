package dev.modichamiya.eclipse.gameplay.social;

import java.util.Objects;

public final class ChatFormatter {public record Formatted(String plain,String miniMessage){}public Formatted format(Rank rank,String player,String message,String channel){Objects.requireNonNull(player);Objects.requireNonNull(message);String prefix=rank==null?"":rank.prefix()+" ",name=rank==null?"#FFFFFF":rank.nameColor(),chat=rank==null?"#FFFFFF":rank.chatColor();String plain="["+channel+"] "+prefix+player+": "+message;String mini="<gray>["+escape(channel)+"]</gray> "+prefix+"<"+name+">"+escape(player)+"</"+name+"><gray>: </gray><"+chat+">"+escape(message)+"</"+chat+">";return new Formatted(plain,mini);}private String escape(String value){return value.replace("<","\\<");}}
