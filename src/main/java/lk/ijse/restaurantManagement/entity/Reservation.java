package lk.ijse.restaurantManagement.entity;

public class Reservation {

        private String reservationId;
        private String description;
        private String cusId;
        private String date;
        private String time;
        private String status;

        public Reservation() {
        }

        public Reservation(String reservationId, String description, String cusId, String date, String time, String status) {
            this.reservationId = reservationId;
            this.description = description;
            this.cusId = cusId;
            this.date = date;
            this.time = time;
            this.status = status;
        }

        public String getReservationId() {
            return reservationId;
        }

        public void setReservationId(String reservationId) {
            this.reservationId = reservationId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCusId() {
            return cusId;
        }

        public void setCusId(String cusId) {
            this.cusId = cusId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ReservationDTO{" +
                    "reservationId='" + reservationId + '\'' +
                    ", description='" + description + '\'' +
                    ", cusId='" + cusId + '\'' +
                    ", date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

