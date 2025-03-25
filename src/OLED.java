class OLED extends Display {
    public OLED(Protocol protocol) {
        super(protocol);
    }

    @Override
    public String getName() {
        return "OLED";
    }
}