/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.prolog;

import java.util.Map;

/**
 * A goal where some terms have been matched.
 * 
 * @author richard
 */
public class PartialGoal {
    private int goalId;
    private int index;
    private Rule rule;
    private PartialGoal parent;
    private Map environment;
}
