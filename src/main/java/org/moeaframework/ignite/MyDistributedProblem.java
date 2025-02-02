/* Copyright 2009-2025 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.moeaframework.ignite;

import java.io.Serializable;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

public class MyDistributedProblem extends AbstractProblem implements Serializable {

	public MyDistributedProblem() {
		super(1, 2);
	}

	@Override
	public void evaluate(Solution solution) {
		double x = RealVariable.getReal(solution.getVariable(0));
		
		// perform some expensive calculation
		double sum = 0.0;
		
		for (int i = 0; i < 1000000; i++) {
			sum += i;
		}
		
		solution.setObjectiveValue(0, Math.pow(x, 2.0));
		solution.setObjectiveValue(1, Math.pow(x - 2.0, 2.0));
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 2);
		solution.setVariable(0, new RealVariable(-10.0, 10.0));
		return solution;
	}

}
