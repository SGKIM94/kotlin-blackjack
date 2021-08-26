# kotlin-blackjack

- [X] 카드가 있다.
    - [X] 문양이 있다. => CardSuite
      - [X] 다이아몬드, 하트, 스페이드, 클로버가 있다.
    - [X] 숫자가 있다. => CardNumber
        - [X] King, Queen, Jack 은 10을 의미한다.
        - [X] 숫자는 2~10까지 있다.
        - [X] Ace는 1 또는 11이다.
    
- [X] 블랙잭은 게임 룰을 가진다.
    - [X] 블랙잭은 처음에 카드를 2장을 가져가면 현재 상태를 리턴한다.
        - [X] 처음 받은 2개의 두 카드의 숫자 합이 21이면 Blackjack 이다.
        - [X] 카드의 숫자의 합이 21미만이면 Hit 이다.
            - [X] 카드의 숫자의 합이 21미만이면 카드를 받을 수 있다.
            - [X] 카드의 숫자의 합이 21미만이면 카드를 더 이상 받지 않을 수 있다.
    - [X] 카드의 숫자의 합이 21을 넘으면 Bust 이다.
    - [X] 게임은 플레이어들과 진행한다.
    - [X] 딜러는 처음에 받은 2장의 합계가 16이하이면 반드시 1장의 카드를 추가로 받아야한다.
    - [X] 딜러는 처음에 받은 2장의 합계가 17점 이상이면 추가로 받을 수 없다.

- [X] 플레이어는 카드를 가진다.
    - [X] 플레이어는 카드 2장을 가지고 시작한다.
    - [X] 플레이어는 카드를 더 받을 수 있다.
    - [X] 플레이어는 카드를 받지 않을 수 있다.
    
- [X] 모든 플레이어가 카드를 받지 않는 상태가 되면 게임이 종료된다.
- [X] 한 게임 당 52장의 카드를 가지고 있다.

- [X] 게임에서는 카드를 한장을 뽑아서 플레이어에게 줄 수 있다. 
  - [X] Cards는 카드를 맨 앞의 카드 한장을 뽑아서 리턴 해야 한다.
  - [X] 못받는 경우는 에러가 발생해야 한다3
- [X] 사람 이름을 받아서, Player를 만들 수 있어야 한다. ( 콤마 기준 )
- [X] 게임이 종료되면 점수를 계산하여 플레이어들의 점수를 보여준다.

- [X] 처음 받은 2장의 카드의 합이 21인 경우에만 블랙잭이 된다.
- [X] 블랙잭의 입력과 출력을 구현한다.

- [X] 블랙잭의 승패를 구한다.
    - [X] 딜러가 21을 초과하면 그 시점까지 남아 있던 플레이어들은 가지고 있는 패에 상관 없이 승리한다.
    - [X] 게임을 완료한 후 각 플레이어별로 승패를 출력한다.

- [X] 플레이어는 배팅 금액을 입력한다.
- [ ] 카드를 추가로 뽑아 21을 초과할 경우 배팅 금액을 모두 잃게 된다.
- [X] 처음 두 장의 카드 합이 21일 경우 블랙잭이 되면 베팅 금액의 1.5배를 딜러에게 받는다.
- [ ] 딜러와 플레이어가 모두 동시에 블랙잭인 경우 플레이어는 배팅한 금액을 돌려받는다.
- [ ] 딜러가 21을 초과하면 그 시점까지 남아 있던 플레이어들은 가지고 있는 패에 상관 없이 승리해 베팅 금액을 받는다.