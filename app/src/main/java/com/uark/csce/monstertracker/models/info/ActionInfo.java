package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class ActionInfo {
    private int type;
    private String actionText;
    private int modifier;
    private List<String> rules;

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getActionText() {
        return actionText;
    }
    public void setActionText(String actionText) {
        this.actionText = actionText;
    }

    public int getModifier() {
        return modifier;
    }
    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public List<String> getRules() {
        return rules;
    }
    public void setRules(List<String> rules) {
        this.rules = rules;
    }
}
