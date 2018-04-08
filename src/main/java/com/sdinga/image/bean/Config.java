package com.sdinga.image.bean;

public class Config {
        private String db;
        private String table;
        private String ak;
        private String sk;
        private String baseUrl;

        public Config() {
        }

        public String getDb() {
            return db;
        }

        public void setDb(String db) {
            this.db = db;
        }

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }

        public String getAk() {
            return ak;
        }

        public void setAk(String ak) {
            this.ak = ak;
        }

        public String getSk() {
            return sk;
        }

        public void setSk(String sk) {
            this.sk = sk;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }