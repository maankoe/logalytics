import networkx as nx

HITS = "entries"
TIMEDIFFS = "timediffs"


class LogGraph:
    def __init__(self, entity_schema):
        self._entity_schema = entity_schema
        self._graph = nx.DiGraph()

    def add_entry(self, entry):
        canonical = self._node_id(entry)
        if entry not in self:
            self._graph.add_node(canonical, **{HITS: [entry]})
        else:
            self.hits(entry).append(entry)
        for candidate, candidate_node in self._graph.nodes.items():
            if candidate == canonical:
                continue
            if candidate_node[HITS][-1].timestamp > entry.timestamp:
                source, target = candidate, canonical
            else:
                source, target = canonical, candidate
            timediff = entry.timestamp - candidate_node[HITS][-1].timestamp
            if self._graph.has_edge(source, target):
                self._graph.edges[source, target][TIMEDIFFS].append(timediff)
            else:
                self._graph.add_edge(source, target, **{TIMEDIFFS: [timediff]})

    def add_entries(self, entries):
        for entry in entries:
            self.add_entry(entry)

    def hits(self, entry):
        return self._graph.nodes[self._node_id(entry)][HITS]

    def has_edge(self, source, target):
        return self._graph.has_edge(self._node_id(source), self._node_id(target))

    def time_diff(self, source, target):
        source = self._node_id(source)
        target = self._node_id(target)
        if self._graph.has_edge(source, target):
            return self._graph.edges[source, target][TIMEDIFFS][0]
        # if self._graph.has_edge(target, source):
        #     return self._graph.edges[target, source][TIMEDIFFS][0]
        else:
            return None

    def __contains__(self, entry):
        return self._node_id(entry) in self._graph

    def __len__(self):
        return len(self._graph)

    def _node_id(self, entry):
        return "-".join(entry.canonical(self._entity_schema))