from abc import ABC, abstractmethod


class EdgeStrategy(ABC):
    @abstractmethod
    def _edges(self, log_graph, entry):
        pass

    def __call__(self, log_graph, entry):
        return self._edges(log_graph, entry)


class FullyConnected(EdgeStrategy):
    def _edges(self, log_graph, entry):
        return log_graph.nodes
