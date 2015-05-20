package kinderuni.ui.components;

/**
 * Created by Georg Plaz.
 */
public interface ButtonComponent extends Component {
    public void addListener(OnClickListener listener);
    public interface OnClickListener{
        public void onClick();
    }
    public String getText();
    public void setText(String text);
}
