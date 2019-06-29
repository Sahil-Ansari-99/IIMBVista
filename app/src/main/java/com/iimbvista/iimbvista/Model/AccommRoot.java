package com.iimbvista.iimbvista.Model;

import java.util.List;

public class AccommRoot {
    public List<AccommRule> Rules;

    public AccommRoot(List<AccommRule> rules) {
        this.Rules = rules;
    }

    public List<AccommRule> getRules() {
        return Rules;
    }
}
