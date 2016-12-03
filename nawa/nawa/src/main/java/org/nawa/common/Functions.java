package org.nawa.common;

/** @brief 수행되어야 할 로직을 객체에 담아놓을 수 있는 클래스
 *        사용시에는 execute 메소드를 구현한후에 해당 로직이 실행되어야 하는 타임에 execute 메소드를 실행한다.
 *        execute 메소드를 구현하는 타임에서 인자를 넘겨야 할 경우에는 생성자의 parameter에 필요한 인자를 집어넣고 execute 메소드내에서 getParameter메소드를 통해 받는다.
 *        execute 메소드를 실행하는 타임에서 인자를 넘겨야 할 경우에는 execute 메소드 자체의 인자를 통해 전달한다
 **/
public abstract class Functions {
    private Object[] mParams;

    /** @brief 생성자. parameter는 execute 메소드내로 전달할 parameter를 전달하기 위해 사용하며 없을 경우 비워놓아도 무방하다.
     * @param params */
    public Functions(Object... params) {
        mParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            mParams[i] = params[i];
        } // for i
    } // constructor

    /** @brief 생성자의 인수목록을 통해 전달된 인수를 execute 메소드내에서 호출하여 가져다 쓰기 위한 메소드.
     *        해당 인수의 index를 입력값으로 한 후에 해당 인수의 원래 데이터형으로 캐스팅하여 받는다.
     * @param index
     * @return */
    public Object getParameter(int index) {
        if (mParams.length <= index) {
            return null;
        } else {
            return mParams[index];
        } // if
    } // Object getParameter

    /** @brief 실행되어야 할 로직을 execute 메소드를 구현하면서 그 안에 담아놓고 나중에 필요시에 execute메소드를 실행한다.
     *        메소드 실행시간에 인수를 넘겨야 하는 경우에 execute 메소드의 인수를 통해 넘기도록 한다.
     * @param params */
    public abstract void execute(Object... params);
} // interface Function