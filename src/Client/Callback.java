package Client;

/**
 *
 * @author KSJaay
 */
public interface Callback<E, O> {

    public void result_set(E firstObject, O SecondObject);
}
