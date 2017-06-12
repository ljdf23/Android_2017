package com.bignerdranch.android.criminalintent.Abstract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.bignerdranch.android.criminalintent.CrimeListFragment;
import com.bignerdranch.android.criminalintent.R;

/**
 * Created by schizophrenia on 7/02/17.
 */


/*
* Notes
*
* dp r dip = density independent pixel: we get the same size regardless of screen density
*            One dp is always 1/160th of an inch on a device’s screen.
* sp = scale independent pixel:  same as dp, and also take into account the user's font size preference
*
* marging vs padding
*
* margin attributes are layout parameters, which means is a call to widget's parent
* padding attibutes tells the widgoet how much bigger than its contents it should draw itself
*
*
* */

//Becuase we use the support library, this class inherate from fragmentactivity instead activity

//region abstract classes explanation
    /*
     * Abstract classes cannot be instantiated, but they can be subclassed.
     *
     * Abstract classes are similar to interfaces. You cannot instantiate them, and they may contain a mix of methods declared
     * with or without an implementation. However, with abstract classes, you can declare fields that are not static and final,
     * and define public, protected, and private concrete methods. With interfaces, all fields are automatically public, static,
     * and final, and all methods that you declare or define (as default methods) are public. In addition, you can extend only one class,
     * whether or not it is abstract, whereas you can implement any number of interfaces.
     *
     * Which should you use, abstract classes or interfaces?

        Consider using abstract classes if any of these statements apply to your situation:
        You want to share code among several closely related classes.
        You expect that classes that extend your abstract class have many common methods or fields, or require access modifiers other than public (such as protected and private).
        You want to declare non-static or non-final fields. This enables you to define methods that can access and modify the state of the object to which they belong.

        Consider using interfaces if any of these statements apply to your situation:
        You expect that unrelated classes would implement your interface. For example, the interfaces Comparable and Cloneable are implemented by many unrelated classes.
        You want to specify the behavior of a particular data type, but not concerned about who implements its behavior.
        You want to take advantage of multiple inheritance of type.
     *
     */
//endregion

    //FragMentActivity => support library’s fragment implementation.

public abstract class SingleFragmentActivity extends FragmentActivity {

    //If a class includes abstract methods, then the class itself must be declared abstract
    //When an abstract class is subclassed, the subclass usually provides implementations
    //for all of the abstract methods in its parent class. However, if it does not, then
    //the subclass must also be declared abstract

    //Abstract methods means there is no default implementation for it and an implementing
    //class will provide the details.
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //The fragmentmanager call the lifecycle methods for every fragment
        //we call getsupportfragmentmanager because we use the suppor library
        FragmentManager fm= getSupportFragmentManager();
        //Get the fragment
        Fragment fragment  = fm.findFragmentById(R.id.fragment_container);
        //Why ask for null?
        //because if the parent activity recreated, before it die, fragmentmanager saves the fragments it has.
        //so, the fragmentmanager could have a fragments to recreate everything as it was before.
        if(fragment == null)
        {
            fragment = createFragment();
            //first parameter: tell the fragment manager where is the fragment, and it uses this id like unique
            //identifier  in  fragment manager.
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
