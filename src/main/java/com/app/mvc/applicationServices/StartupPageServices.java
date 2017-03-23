package com.app.mvc.applicationServices;

import java.io.IOException;
import java.util.List;

public interface StartupPageServices {

    List getAllSubstances();
    List getAllStates();
    List getAllProperties();
    String getDataAsString(List data) throws IOException;
}
