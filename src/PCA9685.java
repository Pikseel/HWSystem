class PCA9685 extends MotorDriver {
    public PCA9685(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "PCA9685";
    }
}