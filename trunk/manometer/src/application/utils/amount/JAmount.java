package application.utils.amount;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 03.03.12
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
public interface JAmount {
    public String getAmount(int Currency,  BigDecimal decimal);
}
