package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        // TODO: Composite aggregation
        // Return total health of all children (and nested children).
        int total = 0;
        for(CombatNode child:children){
            total+=child.getHealth();
        }
        return total;
    }

    @Override
    public int getAttackPower() {
        // TODO: Composite aggregation
        // Return total attack of alive children only.
        int total = 0;
        for(CombatNode child:children){
            if(child.isAlive()){
                total+=child.getAttackPower();
            }
        }
        return total;
    }

    @Override
    public void takeDamage(int amount) {
        // TODO: Composite distribution
        // Distribute incoming damage across alive children.
        // Suggested baseline:
        // 1) Collect alive children
        // 2) Split amount evenly (or using your own documented rule)
        // 3) Apply damage to each child
        List<CombatNode> alive = getAliveChildren();
        if(alive.isEmpty()){
            return;
        }
        int damagePerChild = amount/alive.size();
        for(CombatNode child:alive){
            child.takeDamage(damagePerChild);
        }
    }
    @Override
    public boolean isAlive() {
        // TODO: Composite liveness
        // Return true when at least one child is alive.
        for(CombatNode child:children){
            if(child.isAlive()){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        // TODO: Tree visualization
        // Print this node and recurse into children with increased indent.
        System.out.println(indent + "+ " + name + " [TODO: compute HP/ATK]");
        for(CombatNode child:children){
            child.printTree(indent+" ");
        }
    }

    private List<CombatNode> getAliveChildren() {
        // TODO: helper for takeDamage()
        List<CombatNode> alive = new ArrayList<>();
        for(CombatNode child:children){
            if(child.isAlive()){
                alive.add(child);
            }
        }
        return new ArrayList<>();
    }
}
