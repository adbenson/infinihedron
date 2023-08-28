package infinihedron.control;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalTheme;

public class Theme extends DefaultMetalTheme {
    public String getName() { return "Infinihedron"; }

    private final Color text = Color.WHITE;

    private final Color primary1 = Color.RED;
    private final Color primary2 = Color.GREEN;
    private final Color primary3 = Color.BLUE;

    private final Color secondary1 = Color.ORANGE;
    private final Color secondary2 = Color.YELLOW;
    private final Color secondary3 = Color.BLACK;


    @Override
    public ColorUIResource getControlTextColor() {
        return new ColorUIResource(text);
    }

    @Override
    protected ColorUIResource getPrimary1() { return new ColorUIResource(primary1); }

    @Override
    protected ColorUIResource getPrimary2() { return new ColorUIResource(primary2); }

    @Override
    protected ColorUIResource getPrimary3() { return new ColorUIResource(primary3); }

    @Override
    protected ColorUIResource getSecondary1() { return new ColorUIResource(secondary1); }

    @Override
    protected ColorUIResource getSecondary2() { return new ColorUIResource(secondary2); }

    @Override
    protected ColorUIResource getSecondary3() { return new ColorUIResource(secondary3); }

    // @Override
    // public FontUIResource getControlTextFont() {
    //     return new FontUIResource(super.getControlTextFont().)
    // }

    // @Override
    // public FontUIResource getSystemTextFont() {
    //     // TODO Auto-generated method stub
    //     return null;
    // }

    // @Override
    // public FontUIResource getUserTextFont() {
    //     // TODO Auto-generated method stub
    //     return null;
    // }

    // @Override
    // public FontUIResource getMenuTextFont() {
    //     // TODO Auto-generated method stub
    //     return null;
    // }

    // @Override
    // public FontUIResource getWindowTitleFont() {
    //     // TODO Auto-generated method stub
    //     return null;
    // }

    // @Override
    // public FontUIResource getSubTextFont() {
    //     // TODO Auto-generated method stub
    //     return null;
    // }
}
