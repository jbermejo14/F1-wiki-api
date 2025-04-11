package com.svalero.f1wiki.response;

import java.util.List;

public class DriversResponse {

    private MRData MRData;

    public MRData getMRData() {
        return MRData;
    }

    public static class MRData {

        private DriverTable DriverTable;

        public DriverTable getDriverTable() {
            return DriverTable;
        }

        public static class DriverTable {
            private List<Driver> drivers;

            public List<Driver> getDrivers() {
                return drivers;
            }
        }
    }
}
