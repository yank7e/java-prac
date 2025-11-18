module order.storage {
    requires order.processing;
    exports com.example.order.storage;
    requires order.model;
}