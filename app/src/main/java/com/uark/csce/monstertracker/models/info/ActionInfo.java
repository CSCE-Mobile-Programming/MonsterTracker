package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class ActionInfo {
    private int Type;
    private String ActionText;
    private int Modifier;
    private List<String> Rules;

    public int getType() {
        return Type;
    }
    public void setType(int type) {
        this.Type = type;
    }

    public String getActionText() {
        return ActionText;
    }
    public void setActionText(String actionText) {
        this.ActionText = actionText;
    }

    public int getModifier() {
        return Modifier;
    }
    public void setModifier(int modifier) {
        this.Modifier = modifier;
    }

    public List<String> getRules() {
        return Rules;
    }
    public void setRules(List<String> rules) {
        this.Rules = rules;
    }
}
