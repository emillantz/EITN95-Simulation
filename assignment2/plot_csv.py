import matplotlib.pyplot as plt

def main():
    files = ['task3_5000.csv', 'task3_10000.csv', 'task3_20000.csv']
    files = ['assignment/' + path for path in files]

    means = []
    for file in files:
        with open(file, 'r') as f:
            lines = f.readlines()
            means.append([float(line) for line in lines])

    fig = plt.figure(figsize=(15, 10))
    ax = fig.add_subplot(111)
    bp = ax.boxplot(means, patch_artist=True, notch='True', vert=0)
    
    colors = ['#0000FF', '#00FF00', '#FF0000']
    for patch, color in zip(bp['boxes'], colors):
        patch.set_facecolor(color)

    ax.set_yticklabels(['5000', '10000', '20000'])
    
    plt.show()

if __name__ == '__main__':
    main()
