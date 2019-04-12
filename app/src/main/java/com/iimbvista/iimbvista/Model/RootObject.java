package com.iimbvista.iimbvista.Model;

import java.util.List;

public class RootObject {
    List<Sponsors> Sponsors;

    public RootObject(List<com.iimbvista.iimbvista.Model.Sponsors> sponsors) {
        Sponsors = sponsors;
    }

    public List<com.iimbvista.iimbvista.Model.Sponsors> getSponsors() {
        return Sponsors;
    }
}
