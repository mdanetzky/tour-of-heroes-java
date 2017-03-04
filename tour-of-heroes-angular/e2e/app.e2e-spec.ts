import { MyTestPage } from './app.po';

describe('my-test App', () => {
  let page: MyTestPage;

  beforeEach(() => {
    page = new MyTestPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
