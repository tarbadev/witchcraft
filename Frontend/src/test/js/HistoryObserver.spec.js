import { createHistoryObserver } from 'app-root/HistoryObserver'

describe('createHistoryObserver', () => {
  it('it calls callbacks whose regex matches the history event path with captures', () => {
    const interestingPaths = [
      { regex: /^#\/foos\/(\d+)\/bars\/(\d+)$/, callback: jest.fn() },
      { regex: /^#\/foos\/(\d+)$/, callback: jest.fn() },
    ]

    const historyObserver = createHistoryObserver(interestingPaths)
    const historyEvent = {
      pathname: '/',
      search: '',
      hash: '#/foos/24/bars/13',
      key: 'a8mxyx',
    }

    historyObserver(historyEvent)

    expect(interestingPaths[0].callback).toHaveBeenCalledWith(['24', '13'])
    expect(interestingPaths[1].callback).not.toHaveBeenCalled()
  })
})
