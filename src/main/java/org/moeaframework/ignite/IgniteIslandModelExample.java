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

import java.io.IOException;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.algorithm.extension.Frequency;
import org.moeaframework.core.selection.Selection;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.selection.TournamentSelection;
import org.moeaframework.parallel.island.Island;
import org.moeaframework.parallel.island.IslandModel;
import org.moeaframework.parallel.island.executor.BasicIslandExecutor;
import org.moeaframework.parallel.island.migration.Migration;
import org.moeaframework.parallel.island.migration.SingleNeighborMigration;
import org.moeaframework.parallel.island.topology.FullyConnectedTopology;
import org.moeaframework.parallel.island.topology.Topology;
import org.moeaframework.problem.CEC2009.UF1;
import org.moeaframework.problem.Problem;

public class IgniteIslandModelExample {

	public static void main(String[] args) throws IOException {
		try (Ignite ignite = Ignition.start("config/ignite-config.xml")) {
			Problem problem = new UF1();

			Selection migrationSelection = new TournamentSelection(2, 
					new ChainedComparator(
							new ParetoDominanceComparator(),
							new CrowdingComparator()));

			Migration migration = new SingleNeighborMigration(1, migrationSelection);
			Topology topology = new FullyConnectedTopology();
			IslandModel model = new IslandModel(Frequency.ofEvaluations(1000), migration, topology);

			for (int i = 0; i < 8; i++) {
				NSGAII algorithm = new NSGAII(problem);
				model.addIsland(new Island(algorithm, algorithm.getPopulation()));
			}

			try (BasicIslandExecutor executor = new BasicIslandExecutor(model, ignite.executorService())) {
				executor.run(100000).display();
			}
		}
	}

}
