import argparse
from random import random

def main():
    parser = argparse.ArgumentParser(prog='generate_config.py', description='Generate configuration file for the simulation')
    parser.add_argument('-n', '--nodes', type=int, default=1000,  help='Number of nodes')
    parser.add_argument('--tp', type=float, default=1, help='Time needed to send a message')
    parser.add_argument('--ts', type=float, default=4000, help='Mean of the exponential distribution of the sleep interval')
    parser.add_argument('-r', '--radius', default=7, type=float, help='Radius of the communication range')
    parser.add_argument('--lower', type=float, default=1000, help='Lower bound of the uniform distribution used in subtask c)')
    parser.add_argument('--upper', type=float, default=4000, help='Upper bound of the uniform distribution used in subtask c)')

    args = parser.parse_args()

    with open('assignment/config.csv', 'w') as f:
        f.write(f'{args.nodes},{args.tp},{args.ts},{args.radius},{args.lower},{args.upper}\n')
        for _ in range(args.nodes):
            x = random() * 10
            y = random() * 10
            f.write(f'{x},{y}\n')

if __name__ == '__main__':
    main()
