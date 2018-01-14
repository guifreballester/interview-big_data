#!/usr/bin/python3

f = open("corpus1.txt", "r")
line = f.readline()
words = line.split(" ")[:-1]


def get_words(a, b):
    tempw = words[a - 1:b]
    wdict = {}
    for w in tempw:
        if w in wdict:
            wdict[w] += 1
        else:
            wdict[w] = 1

    wdict = sorted(wdict.items(), key=lambda x: x[1], reverse=True)
    return wdict[:3]


def main():
    try:
        N = int(input())
    except Exception:
        N = 0
    if 1 <= N <= 5000:
        for n in range(N):
            A, B = map(int, input().split())
            if not(1 <= A <= 35000 and 1 <= B <= 35000 and A < B):
                while not (1 <= A <= 35000 and 1 <= B <= 35000 and A < B):
                    print("A, B are invalid")
                    A, B = map(int, input().split())
            st = ""
            for w in get_words(A, B):
                tempw = w[0] + " " + str(w[1]) + ","
                st += tempw
            print("Case #{}: {}".format(n + 1, st))
    elif N == 0:
        print("Number required")
    else:
        print("Number not valid")


if __name__ == "__main__":
    main()
