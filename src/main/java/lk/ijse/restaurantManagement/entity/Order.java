package lk.ijse.restaurantManagement.entity;

public class Order {
        private String orderId;
        private String orderType;
        private String cusId;
        private String date;

        public Order() {
        }

        public Order(String orderId, String orderType, String cusId, String date) {
            this.orderId = orderId;
            this.orderType = orderType;
            this.cusId = cusId;
            this.date = date;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
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

        @Override
        public String toString() {
            return "OrderDTO{" +
                    "orderId='" + orderId + '\'' +
                    ", orderType='" + orderType + '\'' +
                    ", cusId='" + cusId + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }


