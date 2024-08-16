    package com.raufkutayakyildiz.myapplication.Model;


    import com.google.firebase.Timestamp;

    public class Task {
        private String name;
        private String time;
        private Timestamp timestamp;

        public Task(String name, String time, Timestamp timestamp) {
            this.name = name;
            this.time = time;
            this.timestamp = timestamp;
        }

        public Task() {
        }

        // Getter ve setter metodları ekleyin
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }
    }


