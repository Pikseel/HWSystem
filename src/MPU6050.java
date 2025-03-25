class MPU6050 extends IMUSensor {
    public MPU6050(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "MPU6050";
    }
}