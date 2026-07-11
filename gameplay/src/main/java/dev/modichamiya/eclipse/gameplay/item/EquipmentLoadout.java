package dev.modichamiya.eclipse.gameplay.item;

import java.util.*;

public final class EquipmentLoadout {private final EnumMap<EquipmentSlot,ItemInstance>equipped=new EnumMap<>(EquipmentSlot.class);public synchronized Optional<ItemInstance>equip(EquipmentSlot slot,ItemInstance item,ItemService service){ItemDefinition definition=service.requireDefinition(item.definition());if(!compatible(slot,definition.slot()))throw new IllegalArgumentException("Wrong equipment slot");return Optional.ofNullable(equipped.put(slot,item));}public synchronized Optional<ItemInstance>unequip(EquipmentSlot slot){return Optional.ofNullable(equipped.remove(slot));}public synchronized Map<EquipmentSlot,ItemInstance>snapshot(){return Map.copyOf(equipped);}private boolean compatible(EquipmentSlot target,EquipmentSlot declared){return target==declared||(declared==EquipmentSlot.RING_1&&(target==EquipmentSlot.RING_1||target==EquipmentSlot.RING_2));}}
