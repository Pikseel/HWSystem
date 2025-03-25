class LCD extends Display {
    public LCD(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "LCD";
    }
}