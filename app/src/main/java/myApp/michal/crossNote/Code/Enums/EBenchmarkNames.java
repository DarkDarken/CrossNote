package myApp.michal.crossNote.Code.Enums;

import myApp.michal.crossNote.Code.Interfaces.IEnumsType;

public enum EBenchmarkNames implements IEnumsType {
    Amanda {public String getName(){ return " minutes"; }},
    Angie {public String getName(){ return " minutes"; }},
    Anna {public String getName(){ return " minutes"; }},
    Barbara {public String getName(){ return " minutes";}},
    Chelsea {public String getName(){ return ""; }},
    Cindy {public String getName(){ return " rounds"; }},
    Diane {public String getName(){ return " minutes"; }},
    Elizabeth {public String getName(){ return " minutes"; }},
    Eva {public String getName(){ return " minutes"; }},
    Fran {public String getName(){ return " minutes"; }},
    Grace {public String getName(){ return " minutes"; }},
    Helen {public String getName(){ return " minutes"; }},
    Isabel {public String getName(){ return " minutes"; }},
    Jackie {public String getName(){ return " minutes"; }},
    Karen {public String getName(){ return " minutes"; }},
    Kelly {public String getName(){ return " minutes"; }},
    Lynne {public String getName(){ return " rep"; }},
    Linda {public String getName(){ return " minutes"; }},
    Mary {public String getName(){ return " rounds"; }},
    Nancy {public String getName(){ return " minutes"; }},
    Nicole {public String getName(){ return " rounds"; }},
    Back {public String getName() { return ""; }};

    @Override
    public String toString() {
        return getName();
    }
}
