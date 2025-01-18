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

import java.util.concurrent.ExecutorService;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.parallel.DistributedProblem;
import org.moeaframework.problem.Problem;

public class IgniteMasterSlaveExample {
	
	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start("config/ignite-config.xml")) {
		    ExecutorService executor = ignite.executorService();
					
		    try (Problem problem = new DistributedProblem(new MyDistributedProblem(), executor)) {
		        NSGAII algorithm = new NSGAII(problem);
		        algorithm.run(10000);		
		        algorithm.getResult().display();
		    }
		}
	}

}
