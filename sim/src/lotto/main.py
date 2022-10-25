from random import sample
import numpy as np
from itertools import product

import logging
from pathlib import Path
log_file = Path(__file__).parent.parent.parent / "data" / "lotto.log"

log_format = "%(asctime)s: %(message)s"
logging.basicConfig(format=log_format)
logger = logging.getLogger("lotto")
logger.setLevel(logging.INFO)


class OverlappingTicketFactory:
    def __init__(self, ticket_size):
        self.ticket_size = ticket_size

    @property
    def name(self):
        return "overlapping"

    def create(self):
        tickets = [list(range(self.ticket_size)), list(range(self.ticket_size))]
        tickets[-1][-1] = tickets[-1][-1] + 1
        return tickets


class DistinctTicketFactory:
    def __init__(self, ticket_size):
        self.ticket_size = ticket_size

    @property
    def name(self):
        return "distinct"

    def create(self):
        return [list(range(self.ticket_size)), list(range(self.ticket_size, self.ticket_size * 2))]


class ExponentialPayoffCurve:
    def __init__(self, ticket_size, exponent):
        self.ticket_size = ticket_size
        self.exponent = exponent

    def create(self):
        return [x**self.exponent for x in range(self.ticket_size + 1)]


def calculate_utility(tickets, draw, payoff_curve):
    payoff = 0
    for ticket in tickets:
        num_matches = len(set(ticket).intersection(draw))
        logger.debug(f"{ticket}, {draw}, {payoff_curve[num_matches]}")
        payoff += payoff_curve[num_matches]
    return payoff


class Simulation:
    def __init__(
            self,
            ticket_size,
            max_number,
            ticket_factory,
            payoff_curve_factory
     ):
        self.ticket_size = ticket_size
        self.max_number = max_number
        self.population = list(range(max_number))
        self.tickets = ticket_factory.create()
        self.payoff_curve = payoff_curve_factory.create()

    def run(self, num_simulations):
        logger.debug(f"Max number: {self.max_number}")
        logger.debug(f"Tickets: {self.tickets}")
        logger.debug(f"Payoff curve: {self.payoff_curve}")
        utilities = []
        for i in range(num_simulations):
            draw = sample(self.population, self.ticket_size)
            utility = calculate_utility(self.tickets, draw, self.payoff_curve)
            utilities.append(utility)
        return utilities


def simulate(simulation):
    utilities = simulation.run(num_simulations)
    mean_utility = np.mean(utilities)
    stderr_utility = np.std(utilities) / num_simulations ** 0.5
    logger.debug(f"E[U] = {mean_utility:.3f} +- {stderr_utility:.3f}")
    return mean_utility, stderr_utility


if __name__ == "__main__":
    num_simulations = 100000

    ticket_sizes = [5]
    max_numbers = [50]
    payoff_exponents = [2, 3, 4, 5, 6, 7, 8, 9, 10]

    for Strategy in [OverlappingTicketFactory, DistinctTicketFactory]:
        for ticket_size, max_number, payoff_exponent in product(ticket_sizes, max_numbers, payoff_exponents):
            strategy = Strategy(ticket_size)
            expected_utility, stderr = simulate(Simulation(
                ticket_size,
                max_number,
                strategy,
                ExponentialPayoffCurve(ticket_size, payoff_exponent)
            ))
            logger.info(
                f"{ticket_size}; {max_number}; {payoff_exponent}; "
                f"{strategy.name}; {expected_utility}; {stderr}"
            )
