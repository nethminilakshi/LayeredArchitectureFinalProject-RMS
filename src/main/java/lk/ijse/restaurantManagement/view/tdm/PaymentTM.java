package lk.ijse.restaurantManagement.view.tdm;

public class PaymentTM {
        private String paymentId;
        private String  cusId;
        private String orderId;
        private String payMethod;
        private Double amount;

        public PaymentTM() {
        }

        public PaymentTM(String paymentId, String cusId, String orderId, String payMethod, Double amount) {
            this.paymentId = paymentId;
            this.cusId = cusId;
            this.orderId = orderId;
            this.payMethod = payMethod;
            this.amount = amount;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getCusId() {
            return cusId;
        }

        public void setCusId(String cusId) {
            this.cusId = cusId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "PaymentDTO{" +
                    "paymentId='" + paymentId + '\'' +
                    ", cusId='" + cusId + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", payMethod='" + payMethod + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }

