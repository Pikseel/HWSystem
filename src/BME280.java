class BME280 extends TempSensor {
    public BME280(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "BME280";
    }
}