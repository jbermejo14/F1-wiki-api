package com.svalero.f1wiki.response;

import com.svalero.f1wiki.domain.Driver;

import java.util.List;

public class DriversResponse {

    public MRData MRData;

    public MRData getMRData() {
        return MRData;
    }

    public static class MRData {

        public DriverTable DriverTable;

        public DriverTable getDriverTable() {
            return DriverTable;
        }

        public static class DriverTable {
            private List<Driver> Drivers;

            public List<Driver> getDrivers() {
                return Drivers;
            }
        }
    }
}
