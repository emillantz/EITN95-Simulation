from mpl_toolkits.mplot3d import Axes3D

import matplotlib.pyplot as plt
import numpy as np

def main():
    #files = ['task2_2.csv', 'task2_4.csv', 'task2_-1.csv'] 
    files = ['task2_-1.csv']
    files = ['assignment/' + path for path in files]
    
    times = []
    abs = {}
    for file in files:
        with open(file, 'r') as f:
            lines = f.readlines()
            for line in lines:
                line = line.translate({ord('['): None , ord(']'): None}) 
                nums = [float(num) for num in line.split(',')]
                
                time = nums[0]
                id = nums[1]
                f = nums[2:]
                times.append(time)
                print(f)
                if id not in abs.keys():
                    abs[id] = f
                else:
                    abs[id] = [sum(x) for x in zip(abs[id], f)]
                    print(abs[id])
    
    #3d histogram
    fig1 = plt.figure(1)
    axs = fig1.add_subplot(111, projection='3d')
    data = np.array(list(abs.values()))
    data = data / 1000

    x, y = np.meshgrid(np.arange(data.shape[1]), np.arange(data.shape[0]))

    x = x.flatten()
    y = y.flatten()
    z = data.flatten()
    
    axs.bar3d(x, y, np.zeros(len(z)), 1, 1, z)

    #Distribution
    fig2 = plt.figure(2)
    axs = fig2.add_subplot(111)
    data = np.array(times)
    plt.hist(data, bins=100)

    plt.show()



if __name__ == '__main__':
    main()
