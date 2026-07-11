package dev.modichamiya.eclipse.world;
public record WorldPoint(String world,double x,double y,double z) { public WorldPoint { if(world==null||world.isBlank())throw new IllegalArgumentException("world"); } }
