class DHT11 extends TempSensor {
    public DHT11(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "DHT11";
    }
}