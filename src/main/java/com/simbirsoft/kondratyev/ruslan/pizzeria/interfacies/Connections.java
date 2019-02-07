package com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies;

import java.sql.Connection;

public interface Connections {
   void formURLBase();
   Connection getConnection();
}
