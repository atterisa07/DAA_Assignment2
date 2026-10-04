import pandas as pd
import matplotlib.pyplot as plt
import os

df = pd.read_csv("results/results.csv")

os.makedirs("results/plots", exist_ok=True)


def save_time_plot(data, title, filename):
    plt.figure()

    for label, group in data.groupby("structure"):
        group = group.sort_values("n")
        plt.plot(
            group["n"],
            group["time_ms"],
            marker="o",
            label=label
        )

    plt.xscale("log")
    plt.xlabel("n")
    plt.ylabel("Time (ms)")
    plt.title(title)
    plt.legend()
    plt.grid(True)

    plt.savefig(
        "results/plots/" + filename,
        dpi=300,
        bbox_inches="tight"
    )

    plt.close()


def save_metric_plot(data, title, filename):
    plt.figure()

    for structure, group in data.groupby("structure"):
        group = group.sort_values("n")

        for metric in ["steps", "moves", "comparisons"]:
            if group[metric].sum() > 0:
                plt.plot(
                    group["n"],
                    group[metric],
                    marker="o",
                    label=f"{structure} - {metric}"
                )

    plt.xscale("log")
    plt.yscale("log")
    plt.xlabel("n")
    plt.ylabel("Count")
    plt.title(title)
    plt.legend()
    plt.grid(True)

    plt.savefig(
        "results/plots/" + filename,
        dpi=300,
        bbox_inches="tight"
    )

    plt.close()


# W1
w1 = df[df["workload"] == "W1"]

save_time_plot(
    w1,
    "W1 Random Access - Time vs n",
    "W1_time.png"
)

save_metric_plot(
    w1,
    "W1 Random Access - Metrics vs n",
    "W1_metrics.png"
)


# W2
w2 = df[df["workload"] == "W2"]

save_time_plot(
    w2,
    "W2 Search - Time vs n",
    "W2_time.png"
)

save_metric_plot(
    w2,
    "W2 Search - Metrics vs n",
    "W2_metrics.png"
)


# W3 HEAD
w3_head = df[
    (df["workload"] == "W3") &
    (df["variant"] == "head")
]

save_time_plot(
    w3_head,
    "W3 Head Insert/Remove - Time vs n",
    "W3_head_time.png"
)

save_metric_plot(
    w3_head,
    "W3 Head Insert/Remove - Metrics vs n",
    "W3_head_metrics.png"
)


# W3 MIDDLE
w3_middle = df[
    (df["workload"] == "W3") &
    (df["variant"] == "middle")
]

save_time_plot(
    w3_middle,
    "W3 Middle Insert/Remove - Time vs n",
    "W3_middle_time.png"
)

save_metric_plot(
    w3_middle,
    "W3 Middle Insert/Remove - Metrics vs n",
    "W3_middle_metrics.png"
)


# W4
w4 = df[df["workload"] == "W4"]

save_time_plot(
    w4,
    "W4 MinHeap - Time vs n",
    "W4_time.png"
)

save_metric_plot(
    w4,
    "W4 MinHeap - Metrics vs n",
    "W4_metrics.png"
)

print("All plots created successfully.")