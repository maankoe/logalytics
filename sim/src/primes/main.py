import logging
from pathlib import Path

log_file = Path(__file__).parent.parent.parent / "data" / "primes.log"

log_format = "%(asctime)s:%(message)s"
logging.basicConfig(filename=log_file, filemode="w", format=log_format)
logger = logging.getLogger("primes")
logger.setLevel(logging.DEBUG)


def is_prime(x):
    for i in range(2, x):
        if x % i == 0:
            return False
    return True


def gen_primes(max_val):
    for x in range(2, max_val):
        logger.info(x)
        if is_prime(x):
            yield x


if __name__ == "__main__":
    primes = gen_primes(100000)
    print(list(primes))
