package com.example.nikma.firebasestorage;

public class Upload {
        private String name;
        private String uri;

        public Upload(String name, String uri) {
            this.name = name;
            this.uri = uri;
        }

        public Upload() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }


