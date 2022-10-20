import logging
from pathlib import Path

import random

log_file = Path(__file__).parent.parent.parent / "data" / "primes.log"

log_format = "%(asctime)s: %(message)s"
logging.basicConfig(filename=log_file, filemode="w", format=log_format)
logger = logging.getLogger("primes")
logger.setLevel(logging.DEBUG)


def is_prime(x):
    for i in range(2, x):
        if x % i == 0:
            return False
    return True


def gen_primes(candidates):
    for x in candidates:
        logger.info("Testing: {0}".format(x))
        if is_prime(x):
            yield x


def gen_candidates(n_candidates, max_val):
    logger.info("Candidates: {0}, max val: {1} ".format(n_candidates, max_val))
    return (random.randint(2, max_val) for _ in range(n_candidates))


if __name__ == "__main__":
    for _ in range(5):
        primes = gen_primes(gen_candidates(100, 1000000))
        print(list(primes))
