#!/usr/bin/python3
import json

events = []

name_of_file = "events.json"

sub_set = []

city = "Barcelona"


def dict_raise_on_duplicates(ordered_pairs):
    """Reject duplicate keys."""
    d = []
    event = {}
    for k, v in ordered_pairs:
        event[k] = v
        if k == "granularity":
            events.append(event)
    return d


def main():
    with open(name_of_file) as json_file:
        data = json.load(json_file)

    with open(name_of_file) as f:
        json.load(f, object_pairs_hook=dict_raise_on_duplicates)
        data["result"]["event"] = events

    # Creat valid JSON
    with open("events_1.json", 'w') as outfile:
        json.dump(data, outfile)

    # Length of array of events
    print("Number of events: " + str(len(data["result"]["event"])))

    # Create subset
    for event in data["result"]["event"]:
        if city in event["description"]:
            sub_set.append(event)
    data["result"]["event"] = sub_set
    data["result"]["count"] = str(len(data["result"]["event"]))
    with open("events_2.json", 'w') as outfile:
        json.dump(data, outfile)


if __name__ == "__main__":
    main()
