package com.iimbvista.iimbvista.Model;

import java.util.List;
import com.iimbvista.iimbvista.Model.Home;

public class HomeRoot {
    public List<Home> Home;

    public HomeRoot(List<Home> home) {
        Home = home;
    }

    public List<Home> getHome() {
        return Home;
    }
}
