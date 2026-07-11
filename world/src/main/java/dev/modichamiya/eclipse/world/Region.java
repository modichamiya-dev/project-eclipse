package dev.modichamiya.eclipse.world;

import dev.modichamiya.eclipse.registry.ContentKey;
import java.util.*;

public final class Region {
    public record Point(double x,double z){}
    private final ContentKey key; private final String world; private final List<Point> polygon; private final double minY,maxY; private final Map<String,Object> rules;
    public Region(ContentKey key,String world,List<Point> polygon,double minY,double maxY,Map<String,Object> rules){this.key=Objects.requireNonNull(key);this.world=Objects.requireNonNull(world);this.polygon=List.copyOf(polygon);if(polygon.size()<3||minY>maxY)throw new IllegalArgumentException();this.minY=minY;this.maxY=maxY;this.rules=Map.copyOf(rules);}
    public ContentKey key(){return key;} public Map<String,Object> rules(){return rules;}
    public boolean contains(WorldPoint point){if(!world.equals(point.world())||point.y()<minY||point.y()>maxY)return false;boolean inside=false;for(int i=0,j=polygon.size()-1;i<polygon.size();j=i++){Point a=polygon.get(i),b=polygon.get(j);if((a.z()>point.z())!=(b.z()>point.z())&&point.x()<(b.x()-a.x())*(point.z()-a.z())/(b.z()-a.z())+a.x())inside=!inside;}return inside;}
}
